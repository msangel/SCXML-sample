import org.apache.commons.scxml2.Context;
import org.apache.commons.scxml2.Evaluator;
import org.apache.commons.scxml2.SCXMLExecutor;
import org.apache.commons.scxml2.env.SimpleErrorReporter;
import org.apache.commons.scxml2.env.jexl.JexlEvaluator;
import org.apache.commons.scxml2.io.SCXMLReader;
import org.apache.commons.scxml2.model.ModelException;
import org.apache.commons.scxml2.model.SCXML;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.URL;

public class Main {

    // SCXML model source URL
    private static final URL SCXML = Main.class.getResource("/hello.xml");

    public static void main(String[] args) throws ModelException, XMLStreamException, IOException {
        // evaluator instance which is used by SCXML engine to evaluate expressions in SCXML
        Evaluator evaluator = new JexlEvaluator();
        // engine to execute the scxml instance
        SCXMLExecutor executor = new SCXMLExecutor(evaluator, null, new SimpleErrorReporter());

        // parse SCXML URL into SCXML model
        SCXML scxml = SCXMLReader.read(SCXML);
        // set state machine (scxml instance) to execute
        executor.setStateMachine(scxml);

        // create root context storing variables and being used by evaluator
        Context rootContext = evaluator.newContext(null);
        // set the root context for the engine
        executor.setRootContext(rootContext);

        // initiate the execution of the state machine
        executor.go();
    }
}
