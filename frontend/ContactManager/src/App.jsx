import React from 'react'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import ROUTES from './routes/router'
import { ToastContainer } from 'react-toastify'

const routes = createBrowserRouter([...ROUTES])

const App = () => {
  return (
    <div>
      <ToastContainer/>
      <RouterProvider router={routes}/>
    </div>
  )
}

export default App