package dev.andersonfernandes.views;

import java.util.Scanner;

public abstract class BaseViews {
    protected Scanner in;

    public BaseViews(Scanner in) {
        this.in = in;
    }

    protected abstract void menu();
    protected abstract void newResource();
    protected abstract void getResource();
}
