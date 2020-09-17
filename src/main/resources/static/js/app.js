window.addEventListener('DOMContentLoaded', event => {

    //Sidebar Toggler
    const sidebarToggler = document.getElementById('sidebar-toggler')
    //Get A Reference To The Sidebar
    const sidebar = document.getElementById('sidebar')

    //Options Menu
    const optionsMenu = document.getElementById('options-menu')

    //Dropdown Menu
    const dropdownMenu = document.getElementById('dropdownMenu')

    //Toggle the sidebar
    if (sidebarToggler) {
        sidebarToggler.addEventListener('click', sidebarTogglerEvent => {

            if (sidebar) {
                if (sidebar.classList.contains('hidden')) sidebar.classList.remove('hidden')
                else sidebar.classList.add('hidden')
            }

        })
    }

    //Toggle Dropdown Menu
    if (optionsMenu) {

        optionsMenu.addEventListener('click', optionsMenuEvent => {

            if (dropdownMenu) {
                if (dropdownMenu.classList.contains('hidden')) dropdownMenu.classList.remove('hidden')
                else dropdownMenu.classList.add('hidden')
            }

        })
    }

})