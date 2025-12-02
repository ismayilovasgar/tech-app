package com.ismayilov.techapp.dto.response.mbdto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "ValType")
@XmlAccessorType(XmlAccessType.FIELD)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValTypeResponseDTO implements Serializable {
    static final long serialVersionUID = 1L;

    @XmlElement(name = "Valute")
    List<ValuteResponseDTO> valuteList;

    @XmlAttribute(name = "Type")
    String type;

}
