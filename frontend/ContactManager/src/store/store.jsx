import { configureStore } from "@reduxjs/toolkit";
import { contactApi } from "../service/contactApi";
import { setupListeners } from "@reduxjs/toolkit/query";

export const store = configureStore({
  reducer: {
    [contactApi.reducerPath]: contactApi.reducer,
  },
  middleware: (getDefaultMiddelware) => {
    return getDefaultMiddelware().concat(contactApi.middleware);
  },
});

setupListeners(store.dispatch);
