# Prior Assignment for Co-op 2025
โจทย์: transform data จาก raw data จาก Node base ให้อยู่ในรูปแบบ Array index based <br>
raw data link: https://storage.googleapis.com/maoz-event/rawdata.txt

ไฟล์ประกอบด้วย 1) โค้ดภาษา Java และ 2) ไฟล์ Jar สำหรับจัดการข้อมูล JSON โดยวิธีการรันใช้คำสั่ง <br>
>javac -cp ".;lib/json-20250107.jar" PriorAssignment.java <br>
>java -cp ".;lib/json-20250107.jar" PriorAssignment

Output
>Nodes = [input, org.maoz.prehandle.workers.neoai.aiclient.embedding.VoyageVerticle, org.maoz.prehandle.workers.neoai.httpclient.HttpClientAdapterVerticle, org.maoz.prehandle.workers.neoai.aiclient.embedding.util.VoyageTransformVerticle, org.maoz.prehandle.workers.neoai.ebtransform.ToPublishVerticle , org.maoz.prehandle.workers.neoai.notify.LineVerticle, org.maoz.prehandle.workers.neoai.notify.FacebookVerticle, org.maoz.prehandle.workers.neoai.notify.DiscordVerticle, org.maoz.prehandle.workers.neoai.output.OutputVerticle]

>addressIn = ['', voyage-embed-node-2, http-client-adapter-verticle-node-4, voyage-transform-node-3, to-publish-verticle-node-10, line-node-7, facebook-node-8, discord-node-9, output-node-10]

>addressOut = [voyage-embed-node-2, http-client-adapter-verticle-node-4, voyage-transform-node-3, to-publish-verticle-node-10, line-node-7, facebook-node-8, discord-node-9, output-node-10, '', '', '', '']
<br>

### จัดทำโดย
นางสาวพิชญา พิมพ์มหาศิริ (มีน) <br>
นักศึกษาวิทยาการคอมพิวเตอร์ ชั้นปีที่ 3 <br>
ภาควิชาคอมพิวเตอร์ คณะวิทยาศาสตร์ มหาวิทยาลัยศิลปากร
