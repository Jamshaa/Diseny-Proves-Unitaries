package mocks;

import services.DecisionMakingAI;
import services.Suggestion;
import services.exceptions.AIException;
import services.exceptions.BadPromptException;

import java.util.List;

public class AIStubHappyPath implements DecisionMakingAI {

    public boolean initCalled = false;
    public String lastPrompt = null;

    private final String answer;
    private final List<Suggestion> suggestions;

    public AIStubHappyPath(String answer, List<Suggestion> suggestions) {
        this.answer = answer;
        this.suggestions = suggestions;
    }

    @Override
    public void initDecisionMakingAI() throws AIException {
        initCalled = true;
    }

    @Override
    public String getSuggestions(String prompt) throws BadPromptException {
        lastPrompt = prompt;
        return answer;
    }

    @Override
    public List<Suggestion> parseSuggest(String aiAnswer) {
        return suggestions;
    }
}

