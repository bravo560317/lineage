package com.lineage.server.model;

public class L1TaxCalculator
{
    private static final int WAR_TAX_RATES = 15;
    private static final int NATIONAL_TAX_RATES = 10;
    private static final int DIAD_TAX_RATES = 10;
    private final int _taxRatesCastle;
    private final int _taxRatesTown;
    private final int _taxRatesWar = 15;
    
    public L1TaxCalculator(int merchantNpcId) {
        this._taxRatesCastle = L1CastleLocation.getCastleTaxRateByNpcId(merchantNpcId);
        this._taxRatesTown = L1TownLocation.getTownTaxRateByNpcid(merchantNpcId);
    }
    
    public int calcTotalTaxPrice(int price) {
        int taxCastle = price * this._taxRatesCastle;
        int taxTown = price * this._taxRatesTown;
        int taxWar = price * 15;
        return (taxCastle + taxTown + taxWar) / 100;
    }
    
    public int calcCastleTaxPrice(int price) {
        return price * this._taxRatesCastle / 100 - this.calcNationalTaxPrice(price);
    }
    
    public int calcNationalTaxPrice(int price) {
        return price * this._taxRatesCastle / 100 / 10;
    }
    
    public int calcTownTaxPrice(int price) {
        return price * this._taxRatesTown / 100;
    }
    
    public int calcWarTaxPrice(int price) {
        return price * 15 / 100;
    }
    
    public int calcDiadTaxPrice(int price) {
        return price * 15 / 100 / 10;
    }
    
    public int layTax(int price) {
        return price + this.calcTotalTaxPrice(price);
    }
}
