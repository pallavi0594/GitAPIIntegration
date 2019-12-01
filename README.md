# Git API Integration
## Description:
In this project, user can search github and gitlab projects. Along with searching user can filter project based on ownership.

## Web Service Details:
In this project, I have created web service as 
</br><strong>api/user/getProjects/{username}?type=owner</strong>. 
</br><strong>username:</strong> username of github or gitlab
</br><strong>type:</strong> can be one of all, owner, member. Default: owner
</br><strong>Response of service:</strong> This service return combine response of github and gitlab projects.
</br>
<strong>Example:</strong></br>
```
[{
    'platform': 'Github',
    'projectDetails': [{....}]
}, {
    'platform': 'Gilab',
    'projectDetails': [{....}]
}]
```
In projectDetails contain, project related information as well owner related info of project.

## Support
Unit test complete
</br>Handled exception


 

