package com.example.product;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class ProductFormDrawer extends Composite<VerticalLayout> {
    ProductFormDrawer(){
        var header = new H2("Product Details");
        var form = new ProductForm();

        var layout = getContent();
        layout.add(header, new Scroller(form));
        layout.setWidth("300px");
        addClassName(LumoUtility.BoxShadow.MEDIUM);
    }
}
