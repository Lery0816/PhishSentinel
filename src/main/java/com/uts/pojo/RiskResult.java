package com.uts.pojo;

import com.uts.enums.RiskLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskResult {
    private int listScore;
    private int sslScore;
    private int whoisScore;
    private int totalScore;
    private int structureScore;
    private RiskLevel riskLevel;

    public void calculateRiskLevel() {
        int totalScore=this.getListScore()+this.getSslScore()+this.getWhoisScore()+this.getStructureScore();
        if (totalScore <= 20) {
            this.setRiskLevel(RiskLevel.SAFE);
        }
        else if (totalScore <= 50) {
            this.setRiskLevel(RiskLevel.SUSPICIOUS);
        }
        else if (totalScore <= 70) {
            this.setRiskLevel(RiskLevel.HIGH_RISK);
        }
        else {
            this.setRiskLevel(RiskLevel.DANGEROUS);
        }
    }
}
