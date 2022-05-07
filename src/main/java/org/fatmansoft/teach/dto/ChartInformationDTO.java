package org.fatmansoft.teach.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GuoSurui
 */
@Setter
@Getter
@NoArgsConstructor
public class ChartInformationDTO {
    List<String> labels = new ArrayList<>();
    List<Object> datasets = new ArrayList<>();
    List<String> colors = new ArrayList<>();
}
