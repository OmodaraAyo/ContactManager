import Layout from "../layout/Layout"
import AddContact from "../pages/AddContact"
import ContactDetails from "../pages/contactDetails/ContactDetails"
import ContactUpdate from "../pages/ContactUpdate"


const ROUTES = [
    {
        path: "/",
        element: <Layout/>
    },
    {
        path: "/addContact",
        element: <AddContact/>
    },
    {
        path: "/contactDetails/:contactId",
        element: <ContactDetails/>
    },
    {
        path: "/updateContact/:contactId",
        element: <ContactUpdate/>
    },
    
]
export default ROUTES