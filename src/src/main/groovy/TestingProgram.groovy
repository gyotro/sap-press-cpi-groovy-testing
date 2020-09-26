/*
test Classico di uno script groovy, senza l'uso di un framework
 */
import com.sap.gateway.ip.core.customdev.util.Message;
import org.apache.camel.Exchange
import org.apache.camel.CamelContext
import org.apache.camel.impl.DefaultCamelContext
import org.apache.camel.impl.DefaultExchange


import java.util.HashMap;

// Load Groovy Script
GroovyShell shell = new GroovyShell()
Script script = shell.parse(new File("C:\\Users\\InnovatesApp\\IdeaProjects\\CorsoGroovyCPI\\src\\main\\ProveGroovy\\BuildXMLFattura.groovy"))

// Initialize CamelContext and exchange for the message
CamelContext context = new DefaultCamelContext()
Exchange exchange = new DefaultExchange(context)
Message msg = new Message(exchange)

// Initialize the message body with the input file
def body = new File("C:\\Users\\InnovatesApp\\IdeaProjects\\sap-press-cpi-groovy-testing\\src\\data\\in\\fileFattIn.xml")

// Set exchange body in case Type Conversion is required
exchange.getIn().setBody(body)
msg.setBody(exchange.getIn().getBody())
msg.setHeader("CamelHttpMethod","POST")
msg.setProperty("test", "initial")

// Execute Script
script.processData(msg)
exchange.getIn().setBody(msg.getBody())

// Display Results
println("Body: \r\n ${msg.getBody(String)}")
println("Headers ")
msg.getHeaders().each {k, v -> println("$k = $v")}
println("Properties ")
msg.getProperties().each {k, v -> println("$k = $v")}

File fileOut = new File("C:\\Users\\InnovatesApp\\IdeaProjects\\sap-press-cpi-groovy-testing\\src\\data\\out\\fattOut.xml")
fileOut.write(msg.getBody().toString())