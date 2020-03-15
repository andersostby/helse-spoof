package no.nav.helse.spoof

import javax.xml.namespace.QName
import javax.xml.soap.SOAPMessage
import javax.xml.ws.Dispatch
import javax.xml.ws.Service
import javax.xml.ws.soap.SOAPBinding

internal fun dispatch(port: String = "8443"): Dispatch<SOAPMessage> =
        Service.create(QName("InfotrygdVedtak_v1")).apply {
            addPort(
                    QName("infotrygdVedtak_v1Port"),
                    SOAPBinding.SOAP11HTTP_BINDING,
                    "http://localhost:$port/infotrygd-ws/InfotrygdVedtak/v1"
            )
        }.createDispatch(QName("infotrygdVedtak_v1Port"), SOAPMessage::class.java, Service.Mode.MESSAGE)
