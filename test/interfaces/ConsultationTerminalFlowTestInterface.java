package interfaces;

import org.junit.jupiter.api.Test;

public interface ConsultationTerminalFlowTestInterface {

    @Test
    void initRevisionLoadsHistoryAndPrescriptionTest() throws Exception;

    @Test
    void editPrescriptionAndSetEndingDateTest() throws Exception;

    @Test
    void stampESignatureAndSendHistoryAndPrescriptionTest() throws Exception;
}
