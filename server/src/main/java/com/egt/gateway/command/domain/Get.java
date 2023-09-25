package com.egt.gateway.command.domain;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Get {
    @XmlAttribute
    public String session;
}
