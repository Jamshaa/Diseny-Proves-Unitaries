package mocks;

import services.DecisionMakingAI;
import services.Suggestion;
import services.exceptions.AIException;
import services.exceptions.BadPromptException;

import java.util.List;

public class AIBadPromptStub implements DecisionMakingAI {

    @Override
    public void initDecisionMakingAI() throws AIException {
        // OK
    }

    @Override
    public String getSuggestions(String prompt) throws BadPromptException {
        throw new BadPromptException("Bad prompt");
    }

    @Override
    public List<Suggestion> parseSuggest(String aiAnswer) {
        return List.of();
    }
}
