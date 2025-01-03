import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query";

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
        })
    })
});

export const {useAddContactMutation} = contactApi