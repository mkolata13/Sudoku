package pl.comp.view;

import java.util.ListResourceBundle;

public class Authors_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"authors", "The authors of the application are: Jakub Hynasinski, Mateusz Kolata"},
                {"app.title", "Authors"}
        };
    }
}