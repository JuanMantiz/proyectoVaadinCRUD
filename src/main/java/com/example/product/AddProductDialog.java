package com.example.product;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;

import java.awt.*;

public class AddProductDialog extends Dialog {
    @FunctionalInterface
    interface SaveCallback {
        void save(ProductDetails productDetails);
    }
    @FunctionalInterface
    interface ErrorCallback {
        void handleException(RuntimeException e);
    }
    private final SaveCallback saveCallback;
    private final ErrorCallback errorCallback;
    private final ProductForm form;
    AddProductDialog(SaveCallback saveCallback, ErrorCallback errorCallback) {
        this.saveCallback = saveCallback;
        this.errorCallback = errorCallback;

// Crear componentes
        form = new ProductForm();
        form.setFormDataObject(new ProductDetails());
        var saveButton = new Button("Save", e -> save());
        saveButton.addThemeVariants(ButtonVariant.PRIMARY);
        var cancelButton = new Button("Cancel", e-> close());
// Disposición del diálogo
        setHeaderTitle("Add Product");
        add(form);
        getFooter().add(cancelButton, saveButton);
    }
    private void save() {
        form.getFormDataObject().ifPresent(productDetails -> {
            try {
                saveCallback.save(productDetails);

                close();
            } catch (RuntimeException e) {
                errorCallback.handleException(e);
            }
        });
    }
}
