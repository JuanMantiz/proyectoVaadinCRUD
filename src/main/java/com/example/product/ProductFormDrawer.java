package com.example.product;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.jspecify.annotations.Nullable;

public class ProductFormDrawer extends Composite<VerticalLayout> {
    @FunctionalInterface
    interface SaveCallback {
        ProductDetails save(ProductDetails productDetails);
    }
    @FunctionalInterface
    interface ErrorCallback {
        void handleException(RuntimeException e);
    }
    @FunctionalInterface
    interface DeleteCallback {
        void delete(ProductDetails productDetails);
    }
    private final DeleteCallback deleteCallback;
    private final SaveCallback saveCallback;
    private final ErrorCallback errorCallback;
    private final ProductForm form;
    ProductFormDrawer(SaveCallback saveCallback, ErrorCallback errorCallback, DeleteCallback deleteCallback ) {
        this.saveCallback = saveCallback;
        this.errorCallback = errorCallback;
        this.deleteCallback = deleteCallback;
        var header = new H2("Product Details");
        form = new ProductForm();

        var saveButton = new Button("Save", e -> save());
        saveButton.addThemeVariants(ButtonVariant.PRIMARY);
        var deleteButton = new Button("Delete", e -> delete());
        deleteButton.addThemeVariants(ButtonVariant.ERROR);

        var layout = getContent();
        layout.add(header, new Scroller(form));
        layout.add(saveButton, deleteButton);
        layout.setWidth("300px");
        addClassName(LumoUtility.BoxShadow.MEDIUM);
        setVisible(false);


    }
    public void setProductDetails(@Nullable ProductDetails producDetails){
        form.setFormDataObject(producDetails);
        setVisible(producDetails != null);
    }
    private void save() {
        form.getFormDataObject().ifPresent(productDetails -> {
            try { // <- Es buena idea meter esto en un try-catch igual que la eliminación
                var saved = saveCallback.save(productDetails);
                form.setFormDataObject(saved);
            } catch (RuntimeException e) {
                errorCallback.handleException(e);
            }
        });
    }
    private void delete() {
        form.getFormDataObject().ifPresent(productDetails -> {
            Dialog dialog = new Dialog();
            dialog.setHeaderTitle("Confirm deletion");
            dialog.add(new Paragraph(
                    "Are you sure you want to delete the product \""
            + productDetails.getName()
                    + "\"?"
));
            Button cancelButton = new Button("Cancel", e -> dialog.close());
            Button deleteButton = new Button("Delete", e -> {
                try {
                    deleteCallback.delete(productDetails);
                    form.setFormDataObject(null);
                    dialog.close();
                } catch (RuntimeException ex) {
                    dialog.close();
                    errorCallback.handleException(ex);
                }
            });
            deleteButton.addThemeVariants(ButtonVariant.ERROR);
            dialog.getFooter().add(cancelButton, deleteButton);
            dialog.open();

        });
    }
}
