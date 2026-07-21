package com.example.product;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.springframework.dao.OptimisticLockingFailureException;
import com.vaadin.flow.component.button.Button;
import org.springframework.dao.DataIntegrityViolationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("Product Catalog")
public class ProductView extends HorizontalLayout {

    public ProductView(ProductService service) {

        var searchField = new TextField();
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        var grid = new Grid<ProductItem>();

        grid.addColumn(ProductItem::name).setHeader("Name").setSortProperty(ProductItem.SORT_PROPERTY_NAME);
        grid.addColumn(ProductItem::price).setHeader("Price").setSortProperty(ProductItem.SORT_PROPERTY_PRICE);
        grid.addColumn(ProductItem::description).setHeader("Description").setSortProperty(ProductItem.SORT_PROPERTY_DESCRIPTION);
        grid.addColumn(ProductItem::category).setHeader("Category").setSortProperty(ProductItem.SORT_PROPERTY_CATEGORY);
        grid.addColumn(ProductItem::brand).setHeader("Brand").setSortProperty(ProductItem.SORT_PROPERTY_BRAND);

        grid.setItemsPageable(pageable -> service
                .findItems(searchField.getValue(), pageable)
);

        var drawer = new ProductFormDrawer (
                productDetails -> {
            var saved = service.save(productDetails);
            grid.getDataProvider().refreshAll();
            showSuccessNotification("Product updated successfully.");
            return saved;
        },
                this::handleException,
        productDetails -> {
            service.delete(productDetails.getProductId());
            grid.deselectAll();
            grid.getDataProvider().refreshAll();
            showSuccessNotification("Product deleted successfully.");
        });

        searchField.addValueChangeListener(e -> grid.getDataProvider().refreshAll());

        grid.addSelectionListener(event -> {
            var productDetails = event.getFirstSelectedItem()
                    .flatMap(item -> service.findDetailsById(item.productId()))
                             .orElse(null);
            drawer.setProductDetails(productDetails);

        });
        var addButton = new Button("Add Product", e ->
        new AddProductDialog(
                productDetails -> {
                    var saved = service.save(productDetails);
                    grid.getDataProvider().refreshAll();
                    service.findItemById(saved.getProductId())
                            .ifPresent(grid::select);
                    return saved;
        },
        this::handleException
).open()
);
        //layout view
        setSizeFull();
        setSpacing(false);

        var toolbar = new HorizontalLayout();
        toolbar.setWidthFull();
        toolbar.addToStart(searchField);
        toolbar.addToEnd(addButton);

        var listLayout = new VerticalLayout(toolbar, grid);
        listLayout.setSizeFull();
        add(listLayout, drawer);
        setFlexShrink(0, drawer);

    }
    private void handleException(RuntimeException exception) {
        if (exception instanceof OptimisticLockingFailureException) {
            var notification = new Notification(
                    "Another user has edited the same product. "
            + "Please refresh and try again.");
            notification.setPosition(Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.WARNING);
            notification.setDuration(3000);
            notification.open();
        } else if (exception instanceof DataIntegrityViolationException) {
            var notification = new Notification(
                    "El SKU ya está en uso.Por favor, introduzca otro.");
            notification.setPosition(Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.WARNING);
            notification.setDuration(3000);
            notification.open();

        } else {
// Delegate to Vaadin&#39;s default error handler
            throw exception;
        }
    }
    private void showSuccessNotification(String message) {
        Notification notification = new Notification(message, 3000, Notification.Position.TOP_END);
        notification.addThemeVariants(NotificationVariant.SUCCESS);
        notification.open();
    }
}
