import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const contactApi = createApi({
  reducerPath: "contactApi",
  baseQuery: fetchBaseQuery({ baseUrl: "http://localhost:8080/api/contact" }),
  endpoints: (builder) => ({
    addContact: builder.mutation({
      query: (contactData) => ({
        url: "/addContact",
        method: "POST",
        body: contactData,
      }),
    }),

    getContactById: builder.query({
      query: (contactId) => ({
        url: `/getContactById/${contactId}`,
        method: "GET",
      }),
    }),

    getAllContact: builder.query({
      query: () => ({
        url: "/getAllContacts",
        method: "GET",
      }),
    }),

    updateContact: builder.mutation({
      query: ({contactId, contactData}) => ({
        url: `/updateContact/${contactId}`,
        method: "PATCH",
        body: contactData,
      }),
    }),

    deleteContactById: builder.mutation({
        query: (contactId) => ({
            url: `/deleteById/${contactId}`,
            method: "DELETE",
        })
      }),

    searchContactByName: builder.query({
        query: (contactName) => ({
            url: `/getContactByCharacter?character=${contactName}`,
            method: "GET",
        }),
    }),
    //Last closing tag...
  }),
  //endpoint closing tag...
});

export const {
  useAddContactMutation,
  useGetContactByIdQuery,
  useGetAllContactQuery,
  useUpdateContactMutation,
  useDeleteContactByIdMutation,
  useSearchContactByNameQuery,
} = contactApi;
