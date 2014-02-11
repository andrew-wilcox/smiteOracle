package com.hirez.smiteoracle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrew on 2/11/14.
 */
public class TieredItem extends Item{

    private Map<String, String> tier1Stats;
    private Map<String, String> tier2Stats;
    private Map<String, String> tier3Stats;

    public TieredItem(Item tier1, Item tier2, Item tier3){
        tier1Stats = tier1.getStats();
        tier2Stats = tier2.getStats();
        tier3Stats = tier3.getStats();
    }

    public Map<String, String> getTier1Stats() {
        return tier1Stats;
    }

    public void setTier1Stats(Map<String, String> tier1Stats) {
        this.tier1Stats = tier1Stats;
    }

    public Map<String, String> getTier2Stats() {
        return tier2Stats;
    }

    public void setTier2Stats(Map<String, String> tier2Stats) {
        this.tier2Stats = tier2Stats;
    }

    public Map<String, String> getTier3Stats() {
        return tier3Stats;
    }

    public void setTier3Stats(Map<String, String> tier3Stats) {
        this.tier3Stats = tier3Stats;
    }
}
