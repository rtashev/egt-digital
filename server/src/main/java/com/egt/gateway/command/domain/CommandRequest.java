package com.egt.gateway.command.domain;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name="command")
//@XmlAccessorType(XmlAccessType.FIELD)
public class CommandRequest implements Serializable {

    @XmlAttribute
    public String id;

    public Enter enter;

    public Get get;

}
