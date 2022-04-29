package org.fatmansoft.teach.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author GuoSurui
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AverageScoreDTO {
    private Double averageScoreForAll;
    private Double averageGPAForAll;
    private Double averageScoreForMajor;
    private Double averageGPAForMajor;
}
