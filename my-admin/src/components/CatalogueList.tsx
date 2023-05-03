import * as React from "react";
import {Datagrid, List, ListProps, TextField} from "react-admin";

export const CatalogueList = (props: ListProps) => (
    <List {...props}>
        <Datagrid>
            <TextField source ="id" />
            <TextField source ="parentId" />
            <TextField source ="createdTime" />
            <TextField source ="userCreatedById" />
            <TextField source ="name" />
            <TextField source ="typeOfFile" />
        </Datagrid>
    </List>
)
