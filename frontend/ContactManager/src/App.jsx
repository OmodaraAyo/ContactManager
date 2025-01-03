import React from 'react'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import ROUTES from './routes/router'

const routes = createBrowserRouter([...ROUTES])

const App = () => {
  return (
    <div>
      <RouterProvider router={routes}/>
    </div>
  )
}

export default App