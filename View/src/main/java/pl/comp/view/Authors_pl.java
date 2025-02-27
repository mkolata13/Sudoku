package pl.comp.view;

import java.util.ListResourceBundle;

public class Authors_pl extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"authors", "Autorzy aplikacji to: Jakub Hynasinski, Mateusz Kolata"},
                {"app.title", "Autorzy"}
        };
    }
}