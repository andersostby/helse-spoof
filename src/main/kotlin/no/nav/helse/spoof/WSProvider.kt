package no.nav.helse.spoof

import org.apache.cxf.endpoint.Server
import org.apache.cxf.ext.logging.LoggingFeature
import org.apache.cxf.jaxws.JaxWsServerFactoryBean
import javax.xml.soap.SOAPMessage
import javax.xml.ws.*
import javax.xml.ws.soap.SOAPBinding

@BindingType(SOAPBinding.SOAP11HTTP_BINDING)
@WebServiceProvider(
        serviceName = "InfotrygdVedtak_v1",
        targetNamespace = "http://nav.no/tjeneste/virksomhet/infotrygdVedtak/v1/Binding",
        portName = "infotrygdVedtak_v1Port"
)
@ServiceMode(value = Service.Mode.MESSAGE)
internal class WSProvider(private val dispatch: Dispatch<SOAPMessage>) : Provider<SOAPMessage> {
    override fun invoke(request: SOAPMessage?): SOAPMessage {
        return try {
            dispatch.invoke(request)
        }catch (e:Exception){
            println(e.stackTrace)
            throw e
        }
    }
}

internal fun server(dispatch: Dispatch<SOAPMessage>, port: String = "8443"): Server =
        JaxWsServerFactoryBean()
                .apply {
                    serviceBean = WSProvider(dispatch)
                    address = "http://0.0.0.0:$port/infotrygd-ws/InfotrygdVedtak/v1"
                    features.add(LoggingFeature())
                }.create().apply { start() }
