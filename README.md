# Brandz Application
Android Kottlin Application that build in clean archticture and best practisies 
## summary
In this Application we have 2 screens
1. BrandsFragment:\
  that shows the brand's data including the items this brand offers
2. DetailsFragment:\
  that opens an item details including iimage slider
## Archticture
We follow the [onion architicture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)\
So we have 3 layers:
- **Domain layer**:
Would execute business logic which is independent of any layer and is just a pure kotlin package with no android specific dependency.

- **Data layer**:
Would dispense the required data for the application to the domain layer by implementing interface exposed by the domain

- **App layer**:  
Would include both domain and data layer and is android specific which executes the UI logic.

![arch](https://github.com/Alinasser96/Brandz/blob/master/pics/arch.jpg)

## Dependency Injection 
We use Dagger Hilt to apply dependency injection\

## **Thanks a lot** ❤️
