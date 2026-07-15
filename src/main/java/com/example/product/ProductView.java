package com.example.product;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("Product Catalog")
public class ProductView extends HorizontalLayout {

    public ProductView(ProductItemRepository repository) {

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

        grid.setItemsPageable(pageable -> repository
                .findByNameContainingIgnoreCase(searchField.getValue(), pageable)
                .getContent()
        );

        var drawer = new ProductFormDrawer();

        searchField.addValueChangeListener(e -> grid.getDataProvider().refreshAll());

        //layout view
        setSizeFull();
        setSpacing(false);

        var listLayout = new VerticalLayout(searchField, grid);
        listLayout.setSizeFull();
        add(listLayout, drawer);
        setFlexShrink(0, drawer);

    }
}
