package com.example.product;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.jspecify.annotations.Nullable;

public class ProductFormDrawer extends Composite<VerticalLayout> {
    private final ProductForm form;
    ProductFormDrawer(){
        var header = new H2("Product Details");
        form = new ProductForm();

        var layout = getContent();
        layout.add(header, new Scroller(form));
        layout.setWidth("300px");
        addClassName(LumoUtility.BoxShadow.MEDIUM);


    }
    public void setProductDetails(@Nullable ProductDetails producDetails){
        form.setFormDataObject(producDetails);
    }
}
