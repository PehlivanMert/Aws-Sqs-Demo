package org.pehlivan.mert.awssqs.aws;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pehlivan.mert.awssqs.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SQSQueueMessageRequest {
    private User body;
    private boolean isDeadLetterQueueTest;
}
