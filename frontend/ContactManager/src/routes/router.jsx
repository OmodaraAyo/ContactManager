import Layout from "../layout/Layout"
import AddContact from "../pages/AddContact"


const ROUTES = [
    {
        path: "/",
        element: <Layout/>
    },
    {
        path: "/AddContact",
        element: <AddContact/>
    }

]
export default ROUTES