package services.stub;

import services.DecisionMakingAI;
import services.Suggestion;
import services.exceptions.AIException;
import services.exceptions.BadPromptException;

import java.util.List;

public class DecisionMakingAIStub implements DecisionMakingAI {
    public boolean initAI = false;
    public boolean parseCalled = false;
    public String receivedAiAnswer = null;

    public String aiAnswer =
            """
                    <I, 123456789012, BEFORELUNCH, 10, 1, 1, DAY, Take with water>
                    <M, 987654321098, , , 2, , , >
                    <E, 640557143200>""";

    @Override
    public void initDecisionMakingAI() throws AIException {
        initAI = true;
    }

    @Override
    public String getSuggestions(String prompt) throws BadPromptException {
        return aiAnswer;
    }

    @Override
    public List<Suggestion> parseSuggest(String aiAnswer) {
        parseCalled = true;
        receivedAiAnswer = aiAnswer;
        return List.of();
    }
}
