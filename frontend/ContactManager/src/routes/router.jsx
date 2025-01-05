import Layout from "../layout/Layout"
import AddContact from "../pages/AddContact"
import ContactDetails from "../pages/contactDetails/ContactDetails"


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
    
]
export default ROUTES