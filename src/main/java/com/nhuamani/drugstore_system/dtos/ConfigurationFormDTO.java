package com.nhuamani.drugstore_system.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigurationFormDTO {

    private String nombreBotica;
    private String ruc;
    private String telefono;
    private String direccion;
    private String moneda;
    private Integer igv;
    private String logo;

}