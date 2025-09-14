package dev.mvr.schedule;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;

import java.util.List;
import java.util.Random;

public class Bot {
    public static void main(String[] args) throws ClientException, ApiException, InterruptedException {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        String token = System.getenv("VK_BOT_TOKEN");
        Integer groupId = Integer.parseInt(System.getenv("VK_GROUP_ID"));
        GroupActor actor = new GroupActor(groupId,token);
        Integer ts = vk.messages().getLongPollServer(actor).execute().getTs();
        Random random = new Random();
        while (true ){
            MessagesGetLongPollHistoryQuery historyQuery =
                    vk.messages().getLongPollHistory(actor).ts(ts);
            List<Message> messages = historyQuery.execute().getMessages().getItems();
            if(!messages.isEmpty()){
                messages.forEach(message -> {
                            try {
                                if(message.getText().equals("Привет")){
                                    vk.messages()
                                            .send(actor)
                                            .message("Привет от бота")
                                            .userId(message.getFromId())
                                            .randomId(random.nextInt(10000))
                                            .execute();
                                }
                                else {
                                    vk.messages()
                                            .send(actor)
                                            .message(message.getText())
                                            .userId(message.getFromId())
                                            .randomId(random.nextInt(10000))
                                            .execute();
                                }
                            }
                            catch (ApiException | ClientException e){
                                e.printStackTrace();
                            }
                        }
                );
            }
            ts = vk.messages().getLongPollServer(actor).execute().getTs();
            Thread.sleep(500);
        }
    }
}
