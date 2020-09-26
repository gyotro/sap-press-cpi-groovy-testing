/*
Il vantaggio del framework Spock è che si possono testare più file alla volta
 */
import com.sap.gateway.ip.core.customdev.util.Message;
import org.apache.camel.Exchange
import org.apache.camel.CamelContext
import org.apache.camel.impl.DefaultCamelContext
import org.apache.camel.impl.DefaultExchange
import spock.lang.Shared
import spock.lang.Specification
import org.junit.internal.AssumptionViolatedException

import java.util.HashMap;

class TestingProgram_withSpock extends Specification
{

    @Shared
    GroovyShell shell = new GroovyShell()
    @Shared
    Script script
    @Shared
    CamelContext context
    Exchange exchange
    Message msg
    private Message msg

    def setupSpec() {
        // Load Groovy Script
        script = shell.parse(new File("C:\\Users\\InnovatesApp\\IdeaProjects\\CorsoGroovyCPI\\src\\main\\groovy\\XML_Message.groovy"))
        context = new DefaultCamelContext()
    }

    def setup() {
        // Initialize CamelContext and exchange for the message
        exchange = new DefaultExchange(context)
        msg = new Message(exchange)
    }

    def 'Scenario 1 - Ordine con Items'(){
        given: 'inizializzazione'
        def msgBody = new File("C:\\Users\\InnovatesApp\\IdeaProjects\\sap-press-cpi-groovy-testing\\src\\data\\in\\testXML.xml")
        exchange.getIn().setBody(msgBody)
        msg.setBody(exchange.getIn().getBody())

        // Execute Script
        when: "esecuzione script"
        script.processData( msg )
        exchange.getIn().setBody(msg.getBody())

        // il Then si usa se io ho già un Output corretto e voglio sapere se lo script soddisfa l'output Atteso
        then: "visualizzo Output"
        msg.getBody(String) == new File("C:\\Users\\InnovatesApp\\IdeaProjects\\sap-press-cpi-groovy-testing\\src\\data\\out\\output1.xml").text.normalize()

    }

}