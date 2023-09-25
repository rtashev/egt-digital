package com.egt.gateway.command.domain;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Enter {

    @XmlAttribute
    public String session;

    public Long timestamp;

    public Long player;
}
