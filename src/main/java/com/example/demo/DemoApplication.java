package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DemoApplication {
    static class OrderInfo {
        String orderId;
        String issueDate;
        String creationDateTime;

        OrderInfo(String orderId, String issueDate, String creationDateTime) {
            this.orderId = orderId;
            this.issueDate = issueDate;
            this.creationDateTime = creationDateTime;
        }
    }

    public static void main(String[] args) {
        try {
            InputStream inputStream = DemoApplication.class.getClassLoader().getResourceAsStream("input.json");

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(inputStream);

            JsonNode content = root.get("content");
            if (content == null || !content.isArray()) {
                System.err.println("The JSON input does not have a 'content' array.");
                return;
            }

            List<OrderInfo> orderInfoList = new ArrayList<>();
            for (JsonNode element : content) {
                String orderId = element.get("orderId").asText();
                String issueDate = element.get("issueDate").asText();
                String creationDateTime = element.get("creationDateTime").asText();
                orderInfoList.add(new OrderInfo(orderId, issueDate, creationDateTime));
            }

            // Write OrderInfo to CSV file
            String csvFilePath = "orderInfo.csv";
            try (FileWriter writer = new FileWriter(csvFilePath);
                 BufferedWriter bw = new BufferedWriter(writer)) {

                bw.write("OrderId,IssueDate,CreationDateTime");
                bw.newLine();

                for (OrderInfo info : orderInfoList) {
                    bw.write(String.format("%s,%s,%s", info.orderId, info.issueDate, info.creationDateTime));
                    bw.newLine();
                }
            }

            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("Number of orders: " + orderInfoList.size());
            System.out.println("Order information has been written to " + csvFilePath);
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
