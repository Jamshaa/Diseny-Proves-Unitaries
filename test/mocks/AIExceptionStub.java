package mocks;

import services.DecisionMakingAI;
import services.Suggestion;
import services.exceptions.AIException;
import services.exceptions.BadPromptException;

import java.util.List;

public class AIExceptionStub implements DecisionMakingAI {

    @Override
    public void initDecisionMakingAI() throws AIException {
        throw new AIException("Simulated AI init error");
    }

    @Override
    public String getSuggestions(String prompt) throws BadPromptException {
        return "";
    }

    @Override
    public List<Suggestion> parseSuggest(String aiAnswer) {
        return List.of();
    }
}
