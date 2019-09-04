package com.quick.jdbc.type;

import java.sql.*;

/**
 * jdbc+sqlserver 用来快速验证
 * @author vector
 * @date: 2019/9/3 0003 10:12
 */
public class SqlServiceTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Create a variable for the connection string.
        String connectionUrl = "jdbc:sqlserver://xxx:8838;DatabaseName=xxx;user=xxx;password=xx";


        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = con.createStatement();
        String SQL = "SELECT CandidateId as lbdId,TbdObjectId as tbdId FROM xxxTbdCandidates where CandidateId IN    ('a765029f-090c-4d3c-899b-1c381bc6bca1',\n" +
                " 'aa600512-e9ee-45e0-bb7c-da75e69b6590',\n" +
                " 'a564040b-3db6-446c-bb8b-d58746707ada',\n" +
                " 'aaa8038c-8214-4f8d-9c57-b6cd769b5ddd',\n" +
                " 'aab902f2-58b9-4bcf-9812-863ef6b212b1',\n" +
                " '5f7db9ff-d865-4702-b654-aaa800e35761',\n" +
                " 'a56403d3-ee54-43c5-a80a-e5bafde73569',\n" +
                " 'a98803f4-556c-4373-aa29-138ce1788cca',\n" +
                " '6fe8f482-160b-47f7-b693-aab800d438eb',\n" +
                " 'aa2d027c-d6d0-4dc8-a6cb-dd4c6a33eb9c',\n" +
                " '4cc17b9c-0962-4950-9793-a9a700c54c18',\n" +
                " '8c122c2f-131e-460f-8b15-aab00112a6b2',\n" +
                " 'aa9b0449-34b1-48c2-8094-445ce05e6292',\n" +
                " 'a9460424-23b4-435f-b14b-b5206fb27c9e',\n" +
                " 'aa01045c-cd2d-4959-a02b-db5f2aad5e45',\n" +
                " 'c56f157d-9673-45c2-824e-aaae00cca2f9',\n" +
                " 'a7f203f6-29a0-45b8-82c3-bcd5e107ba59',\n" +
                " '8a137851-8c64-4d77-ad33-aa3c00dfb62b',\n" +
                " '8f0d3ddf-82b4-4439-b253-aab900ecb7e1',\n" +
                " 'aa060421-2b9c-4337-a824-cae4b05bd458',\n" +
                " 'a9300382-fd8c-4424-9283-330d37fa7306',\n" +
                " 'aa6803f2-8431-43f5-a66d-09659290b348',\n" +
                " 'aaac0231-84b9-435c-9851-08707cc8d51a',\n" +
                " '7e3bac90-90c8-4c23-9dfb-aa3c016d35fd',\n" +
                " 'a9e403fd-bd48-424c-9ff3-531e30cb080b',\n" +
                " 'a667031f-5054-4dcf-bfdc-d8a1fc4a9b4c',\n" +
                " 'a8400275-19fc-43f5-8e92-e6feba92c84e',\n" +
                " 'aa7f0409-b4d6-474e-aceb-cf8a50ba8594',\n" +
                " 'a6ae0329-0738-43ec-9baa-d9cc0cba443a',\n" +
                " 'a564040a-49a2-4865-8d0c-5461507d7bd3',\n" +
                " 'aa8003af-00b9-4e5a-aed1-c64bbebdaf36',\n" +
                " 'd285991d-cd4d-48e5-840f-aa3d001a0011',\n" +
                " 'c8531964-eff8-4d4a-b6fb-aa3c0106d0ab',\n" +
                " 'a5640413-0609-465e-977d-5285a590831c',\n" +
                " 'a68e0352-bfad-4e06-8d15-31430a0228cd',\n" +
                " 'aab202fb-4380-47c6-8849-bb5a8a6b8116',\n" +
                " 'f28ccfa3-fc4f-48f5-a1b2-aa3c0146fa34',\n" +
                " 'aa9a02cf-8cd0-4e0e-805a-d78794f34e90',\n" +
                " '68b8ae8e-4b5a-4f12-96a3-aa3c00d60b96',\n" +
                " 'a5640424-738c-4d78-a3d6-08c0fdf53092',\n" +
                " 'aa930345-b210-42c6-ae3e-bad82417e710',\n" +
                " 'a6750434-10de-4602-b1fc-c7a52222a920',\n" +
                " 'aab50291-c939-430e-bc54-7d05ae4e42e0',\n" +
                " 'aa7903df-742d-41ae-a2ba-af6d56e97a15',\n" +
                " 'aaae0297-25ad-4116-a087-83990c08cc3c',\n" +
                " '3ffa5276-7700-4251-9932-aa3c014664fd',\n" +
                " 'aa300344-fc07-45ad-adbf-bb1259fbdaea',\n" +
                " 'aa2a0239-7825-46d3-8eb6-6f3bca35730e',\n" +
                " 'a5640421-05a0-4678-846e-bb0296331ff1');\n";
        long s = System.nanoTime();
        ResultSet rs = stmt.executeQuery(SQL);
        System.out.println((System.nanoTime() - s) / 1_000_000);

        // Iterate through the data in the result set and display it.
        while (rs.next()) {
            System.out.println(rs.getString("lbdId") + " ---> " + rs.getString("tbdId"));
        }
    }
}
