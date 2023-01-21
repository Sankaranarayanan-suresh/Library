package com.library.core.repository.library;

public interface LibraryManager {

    void setPlanCost(double oldPlanCost, double newPlanCost);

    double[] getPlanCost();
}
