
import {CatalogueList} from "./components/CatalogueList";
import {BrowserRouter, Route} from "react-router-dom";

import * as React from "react";
import { Admin, Resource } from 'react-admin';
import dataProvider from "./dataProvider";
// import simpleRestProvider from 'ra-data-simple-rest';


// const dataProvider = simpleRestProvider('http://localhost:8080');

// const App = () => (
//     <Admin dataProvider={dataProvider}>
//         <Resource name="catalogue/root" list={CatalogueList} />
//     </Admin>
// );
const App = () => (
    <BrowserRouter>
        <Admin  dataProvider={dataProvider}>
            <Resource name="catalogue/root" list={CatalogueList} />
        </Admin>
    </BrowserRouter>
);

export default App;

