# README Template

Below is a template provided for use when building your README file for students.

# Project Title

Project description goes here.

## Getting Started

Instructions for how to get a copy of the project running on your local machine.

### Dependencies

```
Examples here
```

### Installation

Step by step explanation of how to get a dev environment running.

List out the steps

```
Give an example here
```

## Testing

N/A

### Break Down Tests

N/A

```
Examples here
```

## Project Instructions

This project includes the following required screens:
1- [LoginFragment](starter/app/src/main/java/com/udacity/shoestore/login/presentation/LoginFragment.kt)
2- [WelcomeFragment](starter/app/src/main/java/com/udacity/shoestore/welcome/presentation/WelcomeFragment.kt)
3- [InstructionsFragment](starter/app/src/main/java/com/udacity/shoestore/instructions/presentation/InstructionsFragment.kt)
4- [ShoesListingFragment](starter/app/src/main/java/com/udacity/shoestore/shoesListing/presentation/ShoesListingFragment.kt)
5- [ShoesDetailsFragment](starter/app/src/main/java/com/udacity/shoestore/shoesDetails/presentation/ShoesDetailsFragment.kt)
in addition to [Constants.kt] including constants for Shared Preferences

All xml files include <layout> tag to generate the respective DataBinding classes and an
additional <data> tag is added in fragment_shoes_details.xml Navigation is performed solely with
Jetpack Navigation component.

Some I faced while developing the project:
1-
in [ShoeListingViewModel](starter/app/src/main/java/com/udacity/shoestore/shoesListing/presentation/ShoeListingViewModel.kt)
I couldn't use `newShoes` as holder of data that gets populated
in [fragment_Shoes_details.xml](starter/app/src/main/res/layout/fragment_shoes_details.xml)
Edit views, I thought it can be retrieved as an already filled-in object by using `binding.newShoes`
where `newShoes` is the name of the variable in the xml file and then use that object to add to the
`shoesList` but that didn't work. that's why I used a standalone livedata for each input field (
name, size, company, description )
in [ShoesListingFragment](starter/app/src/main/java/com/udacity/shoestore/shoesListing/presentation/ShoesListingFragment.kt)

2- Not satisfied with the way I used to resize the edit views hints
in [fragment_Shoes_details.xml](starter/app/src/main/res/layout/fragment_shoes_details.xml)

## Built With

* [ConstraintLayout](https://developer.android.com/reference/androidx/constraintlayout/widget/ConstraintLayout)
* [DataBinding](https://developer.android.com/topic/libraries/data-binding)
* [Jetpack Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started)

## License
