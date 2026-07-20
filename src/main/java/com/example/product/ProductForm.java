package com.example.product;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class ProductForm extends Composite<FormLayout> {

    private final Binder<ProductDetails> binder;
    private ProductDetails currentProduct;
    ProductForm() {
        //Componentes
        var nameField = new TextField("Name");
        var descriptionField = new TextField("Description");
        var categoryField = new TextField("Category");
        var brandField = new TextField("Brand");
        var skuField = new TextField("Sku");
        var releaseDateField = new DatePicker("Release Date");
        var priceField = new BigDecimalField("Price");
        var discountField = new BigDecimalField("Discount");


        //Layout del form
        var layout = getContent();
        layout.add(nameField,descriptionField,categoryField,brandField,releaseDateField,priceField,discountField, skuField);

        //Vincular campos
        binder = new Binder<>();

        binder.forField(nameField)
                .asRequired("Enter name")
                .bind(ProductDetails::getName, ProductDetails::setName);
        binder.forField(descriptionField)
                .asRequired("Enter description")
                .bind(ProductDetails::getDescription, ProductDetails::setDescription);
        binder.forField(categoryField)
                .asRequired("Enter category")
                .bind(ProductDetails::getCategory, ProductDetails::setCategory);
        binder.forField(brandField).bind(ProductDetails::getBrand, ProductDetails::setBrand);
        binder.forField(skuField)
                .asRequired("Enter SKU")
                .bind(ProductDetails::getSku, ProductDetails::setSku);
        binder.forField(releaseDateField).bind(ProductDetails::getReleaseDate, ProductDetails::setReleaseDate);
        binder.forField(priceField)
                .asRequired("Enter price")
                .bind(ProductDetails::getPrice, ProductDetails::setPrice);
        binder.forField(discountField).bind(ProductDetails::getDiscount, ProductDetails::setDiscount);

//        binder.setReadOnly(true);


    }

    public void setFormDataObject(@Nullable ProductDetails productDetails){
        // 💡 Ahora sí podemos usar la variable aquí
        this.currentProduct = productDetails;
        if (productDetails != null) {
            binder.readBean(productDetails);
        } else {
            binder.readBean(new ProductDetails());
        }
    }

    public Optional<ProductDetails> getFormDataObject() {
        if (currentProduct == null) {
            System.out.println("❌ ERROR: currentProduct es NULL en el formulario.");
            return Optional.empty();
        }

        // 1. Forzamos una validación manual antes de escribir
        var validation = binder.validate();
        if (!validation.isOk()) {
            System.out.println("⚠️ EL BINDER TIENE ERRORES DE VALIDACIÓN:");
            validation.getFieldValidationErrors().forEach(error -> {
                System.out.println("   - Campo con error: " + error.getField().toString());
                System.out.println("   - Mensaje: " + error.getMessage().orElse("Sin mensaje"));
            });
            return Optional.empty();
        }

        try {
            System.out.println("🔄 Intentando transferir datos al objeto con writeBean...");
            binder.writeBean(currentProduct);
            System.out.println("✅ Datos transferidos con éxito para: " + currentProduct.getName());
            return Optional.of(currentProduct);
        } catch (com.vaadin.flow.data.binder.ValidationException e) {
            System.out.println("❌ Error de Validación en writeBean (Campos incorrectos)");
            return Optional.empty();
        } catch (Exception e) {
            // 🚨 SI HAY UN ERROR DE TIPOS (Ej: Conversión de fechas/números), AQUÍ LO VERÁS
            System.out.println("💥 ERROR CRÍTICO DEL SISTEMA AL GUARDAR:");
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
