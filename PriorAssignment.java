import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class FetchData {
    private String rawDataUrl;

    public FetchData(String rawDataUrl) {
        this.rawDataUrl = rawDataUrl;
    }

    public String request() throws IOException {
        try {
            URL rawDataUrl = new URL(this.rawDataUrl);
            HttpURLConnection connection = (HttpURLConnection) rawDataUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            BufferedReader dataApiReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder dataResponse = new StringBuilder();
            String inputLine;

            while ((inputLine = dataApiReader.readLine()) != null) {
                dataResponse.append(inputLine);
            }
            dataApiReader.close();
            return dataResponse.toString();
        } catch (IOException e) {
            System.out.println("Error fetching data: " +e.getMessage());
            throw e;
        }
    }    
}

class TransformData {
    private List<String> nodes, addressIn, addressOut;

    public TransformData(String data) {
        nodes = new ArrayList<>();
        addressIn = new ArrayList<>();
        addressOut = new ArrayList<>();
        transform(data);
    }
    
    private void transform(String data) {
        JSONObject jsonObj = new JSONObject(data);
        JSONArray jsonNodes = jsonObj.getJSONArray("nodes");
        JSONArray jsonEdges = jsonObj.getJSONArray("edges");

        Map<String, String> nodeTypeMap = new HashMap<>();
        Map<String, List<String>> addressInMap = new HashMap<>();
        Map<String, List<String>> addressOutMap = new HashMap<>();
    
        for (int i = 0; i < jsonNodes.length(); i++) {
            JSONObject node = jsonNodes.getJSONObject(i);   
            String nodeId = node.getString("id");
            String nodeType = node.getString("type");

            nodeTypeMap.put(nodeId, nodeType);
            addressInMap.put(nodeId, new ArrayList<>());
            addressOutMap.put(nodeId, new ArrayList<>());
        }
    
        for (int i = 0; i < jsonEdges.length(); i++) {
            JSONObject edge = jsonEdges.getJSONObject(i);
            String source = edge.getString("source");
            String target = edge.getString("target");
            
            if (addressOutMap.containsKey(source)) {
                addressOutMap.get(source).add(target);
            }
            if (addressInMap.containsKey(target)) {
                addressInMap.get(target).add(source);
            }
        }
        
        String startNodeId = null;
        for (int i = 0; i < jsonNodes.length(); i++) {
            if (jsonNodes.getJSONObject(i).getString("type").equals("input")) {
                startNodeId = jsonNodes.getJSONObject(i).getString("id");
                break;
            }
        }
        if (addressInMap.get(startNodeId).isEmpty()) {
            addressIn.add("''");
        }

        List<String> currentNodes = new ArrayList<>();
        currentNodes.add(startNodeId);
        while (!currentNodes.isEmpty()) {
            String currentNodeId = currentNodes.remove(0);
            nodes.add(nodeTypeMap.get(currentNodeId));

            List<String> nextNodes = addressOutMap.get(currentNodeId); 
            if (nextNodes == null || nextNodes.isEmpty()) { 
                addressOut.add("''");
                continue;
            }
            String nextNodeStr = String.join(", ", nextNodes);
            addressOut.add(nextNodeStr);
            
            for (int i = 0; i < nextNodes.size(); i++) {
                String nextNodeId = nextNodes.get(i);
                addressIn.add(nextNodeId);
                currentNodes.add(nextNodeId);
            }
        }
    }
    
    public void printInfo() {
        System.out.println("Nodes = " + nodes + "\n");
        System.out.println("addressIn = " + addressIn + "\n");
        System.out.println("addressOut = " + addressOut + "\n");
    }
}

public class PriorAssignment {
    public static void main(String[] args) {
        String rawDataUrl = "https://storage.googleapis.com/maoz-event/rawdata.txt";
        try {
            FetchData fetcher = new FetchData(rawDataUrl);
            String data = fetcher.request();
            TransformData transform = new TransformData(data);
            transform.printInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}