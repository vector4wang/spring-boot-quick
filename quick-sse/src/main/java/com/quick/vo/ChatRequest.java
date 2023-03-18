package com.quick.vo;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {

    @Schema(description = "模型ID，目前只支持gpt-3.5-turbo和gpt-3.5-turbo-0301", example = "gpt-3.5-turbo", required = true)
    private String model;

    @Schema(description = "要为其生成聊天内容的消息列表", required = true)
    private List<Message> messages;

    @Schema(description = "抽样温度，范围为0到2。较高的值（如0.8）会使输出更随机，而较低的值（如0.2）会使其更专注和确定性。")
    private Double temperature;

    @Schema(description = "top-p策略的概率阈值，只保留该阈值内的概率之和，范围为0到1。与抽样温度二选一。")
    private Double topP;

    @Schema(description = "每个输入消息要生成的聊天完成选择数量。")
    private Integer n;

    @Schema(description = "是否使用流式传输，如果设置，则会发送部分消息增量，就像ChatGPT一样。Token将作为仅包含数据的服务器发送事件发送，随着它们可用，流将由data: [DONE]消息终止。")
    private Boolean stream;

    @Schema(description = "最多允许生成的答案的最大标记数。默认情况下，模型可以返回的标记数为（4096 -提示标记数）。")
    private Integer maxTokens;

    @Schema(description = "基于它们是否已在到目前为止的文本中出现，对新标记进行惩罚的数字，介于-2.0和2.0之间的数字。正值会增加模型谈论新话题的可能性。")
    private Double presencePenalty;

    @Schema(description = "基于它们到目前为止在文本中的现有频率对新标记进行惩罚的数字，介于-2.0和2.0之间的数字。正值会减少模型直接重复相同内容的可能性。")
    private Double frequencyPenalty;

    @Schema(description = "修改特定标记出现在完成中的可能性。接受将标记（由其在标记器中的标记ID指定）映射到-100到100的关联偏差值的json对象。在采样之前，数学上将偏差添加到模型生成的标志物中的logit。确切的效果将因模型而异，但值介于-1和1之间应该会减少或增加选择的可能性；值为-100或100应该导致禁止或专有选择相关标记。")
    private JSONObject logitBias;

    @Schema(description = "用于表示终端用户的唯一标识符，可帮助OpenAI监视和检测滥用。了解更多。")
    private String user;

    @Schema(description = "当达到以下任意一个序列时，API将停止生成进一步的标记。")
    private JSONArray stop;
}