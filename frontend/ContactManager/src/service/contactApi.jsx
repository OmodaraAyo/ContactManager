import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const contactApi = createApi({
    reducerPath: 'contactApi',
    baseQuery: fetchBaseQuery({baseUrl: 'http://localhost:8080/api/contact'}),
    endpoints: (builder) => ({
        
        addContact: builder.mutation({
            query: (contactData) => ({
                url: '/addContact',
                method: 'POST',
                body: contactData,
            }),
        }),
        
        getContactById: builder.query({
            query: (contactId) => ({
                url: `/getContactById/${contactId}`,
                method: 'GET',
            })
        }),

        getAllContact: builder.query({
            query: () => ({
                url: '/getAllContacts',
                method: 'GET'
            })
        })
    }),
    //endpoint closing tag...
});

export const { useAddContactMutation, useGetContactByIdQuery, useGetAllContactQuery } = contactApi;