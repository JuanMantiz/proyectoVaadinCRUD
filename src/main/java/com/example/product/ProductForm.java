package com.example.product;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.jspecify.annotations.Nullable;


public class ProductForm extends Composite<FormLayout> {

    private final Binder<ProductDetails> binder;
    ProductForm() {
        //Componentes
        var nameField = new TextField("Name");
        var descriptionField = new TextField("Description");
        var categoryField = new TextField("Category");
        var brandField = new TextField("Brand");
        var skuField = new TextField("Sku");
        var releaseDateField = new DatePicker("Release Date");
        var priceField = new BigDecimalField("Release Date");
        var discountField = new BigDecimalField("Discount");


        //Layout del form
        var layout = getContent();
        layout.add(nameField,descriptionField,categoryField,brandField,releaseDateField,priceField,discountField);

        //Vincular campos
        binder = new Binder<>();

        binder.forField(nameField).bind(ProductDetails::getName, ProductDetails::setName);
        binder.forField(descriptionField).bind(ProductDetails::getDescription, ProductDetails::setDescription);
        binder.forField(categoryField).bind(ProductDetails::getCategory, ProductDetails::setCategory);
        binder.forField(brandField).bind(ProductDetails::getBrand, ProductDetails::setBrand);
        binder.forField(skuField).bind(ProductDetails::getSku, ProductDetails::setSku);
        binder.forField(releaseDateField).bind(ProductDetails::getReleaseDate, ProductDetails::setReleaseDate);
        binder.forField(priceField).bind(ProductDetails::getPrice, ProductDetails::setPrice);
        binder.forField(discountField).bind(ProductDetails::getDiscount, ProductDetails::setDiscount);

        binder.setReadOnly(true);

    }

    public void setFormDataObject(@Nullable ProductDetails productDetails){
        binder.setBean(productDetails);
    }
}
