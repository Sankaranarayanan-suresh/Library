package com.library.database.repository;

import com.library.core.repository.library.LibraryManager;

public class LibraryDataManager implements MemberDataProvider , LibraryManager {
    private final double[] planCost = {100, 250, 500, 1000};

    public void setPlanCost(double oldPlanCost, double newPlanCost) {
        for (int i = 0; i < planCost.length; i++) {
            if (planCost[i] == oldPlanCost) {
                planCost[i] = newPlanCost;
            }
        }
    }

    public double[] getPlanCost() {
        return planCost;
    }
}
